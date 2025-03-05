
import 'dotenv/config';
import admin from 'firebase-admin';

const texto = require('../../ejemploimagenes-key.json');


admin.initializeApp({
    credential: admin.credential.cert(texto),
    storageBucket: process.env.BUCKET
});

export const bucket = admin.storage().bucket();
