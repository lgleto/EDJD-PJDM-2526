/**
 * Import function triggers from their respective submodules:
 *
 * const {onCall} = require("firebase-functions/v2/https");
 * const {onDocumentWritten} = require("firebase-functions/v2/firestore");
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */


const {onDocumentCreated} = require("firebase-functions/v2/firestore");
const admin = require("firebase-admin");
admin.initializeApp();

const db = admin.firestore();

// For cost control, you can set the maximum number of containers that can be
// running at the same time. This helps mitigate the impact of unexpected
// traffic spikes by instead downgrading performance. This limit is a
// per-function limit. You can override the limit for each function using the
// `maxInstances` option in the function's options, e.g.
// `onRequest({ maxInstances: 5 }, (req, res) => { ... })`.
// NOTE: setGlobalOptions does not apply to functions using the v1 API. V1
// functions should each use functions.runWith({ maxInstances: 10 }) instead.
// In the v1 API, each function can only serve one request per container, so
// this will be the maximum concurrent request count.


// Create and deploy your first functions
// https://firebase.google.com/docs/functions/get-started

// exports.helloWorld = onRequest((request, response) => {
//   logger.info("Hello logs!", {structuredData: true});
//   response.send("Hello from Firebase!");
// });

class Tip {
    constructor( comment, userId) {
        this.comment = comment;
        this.userId = userId;
    }
}

const tipConverter = {
    toFirestore: (tip) => ({
        comment: tip.comment,
        userId: tip.userId
    }),
    fromFirestore: (snapshot, options) => {
        const data = snapshot.data(options);
        return new Tip( data.comment, data.userId);
    }
};


class User {
    constructor( name, email, bio, token) {
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.token = token;
    }
}

const userConverter = {
    toFirestore: (user) => ({
        name: user.name,
        email: user.email,
        bio: user.bio,
        token: user.token
    }),
    fromFirestore: (snapshot, options) => {
        const data = snapshot.data(options);
        return new User( data.name, data.email, data.bio, data.token);
    }
};

exports.sendNotification = onDocumentCreated("games/{gameId}/tips/{tipId}", (event) => {
    const gameTip = tipConverter.fromFirestore(event.data);

    //get usersIDs in game
    return db.collection("games").doc(event.params.gameId).collection("tips").get().then((snapshot) => {
        const userIds = [];
        snapshot.forEach((doc) => {
            const tip = tipConverter.fromFirestore(doc);
            const userId = tip.userId;
            
            if (!userIds.includes(userId)) {
                userIds.push(userId);
            }
        });
        
        //get user tokens
        const userTokenPromises = userIds.map((userId) => {
            return db.collection("users").doc(userId).get().then((doc) => {
                const user = userConverter.fromFirestore(doc);
                return user.token;
            });
        });
        
        return Promise.all(userTokenPromises).then((tokens) => {
            const userTokens = tokens.filter((token) => token != null);
            
            if (userTokens.length > 0) {
                return admin.messaging().sendEachForMulticast({
                    tokens: userTokens,
                    notification: {
                        title: "New tip",
                        body: gameTip.comment
                    }
                });
            }
        });
    });
});