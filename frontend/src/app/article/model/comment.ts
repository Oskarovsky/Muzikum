import { Post } from './post';
import { User } from 'src/app/services/user/user';

export class Comment {
  id: number;
  text: string;
  post: Post;
  user: User;
}
