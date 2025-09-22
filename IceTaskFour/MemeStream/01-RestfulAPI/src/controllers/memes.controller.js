import Meme from '../models/Meme.js';
import { sendToTopic } from '../config/firebase.js';

export const createMeme = async (req, res, next) => {
  try {
    const { userId, title, imageUrl, caption = '', tags = [], source = '' } = req.body;

    if (!userId || !title || !imageUrl) {
      return res.status(400).json({
        message: 'userId, title and imageUrl are required'
      });
    }

    const meme = await Meme.create({ userId, title, imageUrl, caption, tags, source });
    return res.status(201).json(meme);
  } catch (err) {
    next(err);
  }
};

export const getMemes = async (req, res, next) => {
  try {
    const { userId, tag, search, page = 1, limit = 20 } = req.query;
    const q = {};

    if (userId) q.userId = userId;
    if (tag) q.tags = { $in: [tag] };
    if (search) {
      q.$or = [
        { title: { $regex: search, $options: 'i' } },
        { caption: { $regex: search, $options: 'i' } },
        { tags: { $regex: search, $options: 'i' } }
      ];
    }

    const pageNum = Math.max(parseInt(page) || 1, 1);
    const limitNum = Math.min(Math.max(parseInt(limit) || 20, 1), 100);

    const [items, total] = await Promise.all([
      Meme.find(q)
        .sort({ createdAt: -1 })
        .skip((pageNum - 1) * limitNum)
        .limit(limitNum),
      Meme.countDocuments(q)
    ]);

    res.json({
      items,
      total,
      page: pageNum,
      pages: Math.ceil(total / limitNum)
    });
  } catch (err) {
    next(err);
  }
};

export const getMemeById = async (req, res, next) => {
  try {
    const meme = await Meme.findById(req.params.id);
    if (!meme) return res.status(404).json({ message: 'Meme not found' });
    res.json(meme);
  } catch (err) {
    next(err);
  }
};