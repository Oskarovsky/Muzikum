import { User } from 'src/app/services/user/user';

export class Post {
  id: number;
  content: string;
  title: string;
  user: User;
  description: string;
  createdAt: string;
}
