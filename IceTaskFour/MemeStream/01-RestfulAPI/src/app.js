import express from 'express';
import cors from 'cors';
import morgan from 'morgan';
import memesRouter from './routes/memes.routes.js';

const app = express();
app.use(cors());
app.use(express.json());
app.use(morgan('dev'));

app.get('/', (_req, res) => res.json({ ok: true, service: 'MemeStream API' }));
app.use('/memes', memesRouter);

export default app;