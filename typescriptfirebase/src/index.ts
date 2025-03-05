import { bucket } from "./firebase/firebaseConfig";
import {v4 as uuid} from 'uuid';
import express from 'express';
import cors from 'cors';
import multer from "multer";
import fs from 'node:fs';
import 'dotenv/config';

const app = express();
const port = 3008;
const carpeta = 'ejemplospringtypescript';
const bucketname = process.env.BUCKET;

app.use(cors());
app.use(express.json());

const main = express.Router();

const upload = multer({ dest: 'uploads/' });

app.use('/api/image', main);

async function viewFile(filename:string):Promise<string> {
    const file = bucket.file(filename);
    const [view] = await file.exists();
    if(view){
        const url = `https://firebasestorage.googleapis.com/v0/b/${bucketname}/o/${filename.replace('/', '%2F')}?alt=media`;
        return url;
    }
    return '';
} 

main.get('/', async (req, res) =>{
    const data = req.query.fileName as string;
    const filename = `${carpeta}/${data}`;
    const url = await viewFile(filename);
    res.json({url});

    // const data = req.query.fileName as string;
    // const filename = `${carpeta}/${data}`;

    // const file = bucket.file(filename);

    // const view = file.getSignedUrl({action:'read', expires:'03-09-2030'});

    // res.json({url:await view, name:filename});
    
});

main.post('/', upload.single('file'), async (req, res)=>{
    if(!req.file){
        res.status(400).json({message:'no se puede'})
    }else{
        const filename = `${carpeta}/${uuid()+req.file.originalname}`;
        const filepath = req.file.path;
        await bucket.upload(filepath, {destination:filename});

        fs.unlinkSync(filepath);

        const url = await viewFile(filename);

        res.json({url});
    }
});

main.delete('/:fileName', async (req, res)=>{
    const filename = `${carpeta}/${req.params.fileName}`;
    const file = bucket.file(filename);
    const [view] = await file.exists();
    if(view){
        await file.delete();
    }

    res.sendStatus(204);
})

app.listen(port, ()=>{
    console.log('Port:' + port);
});