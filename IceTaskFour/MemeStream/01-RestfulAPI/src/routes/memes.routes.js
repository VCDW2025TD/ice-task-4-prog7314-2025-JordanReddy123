import { Router } from 'express';
import { createMeme, getMemes, getMemeById } from '../controllers/memes.controller.js';

const router = Router();

router.post('/', createMeme);
router.get('/', getMemes);
router.get('/:id', getMemeById);

export default router;