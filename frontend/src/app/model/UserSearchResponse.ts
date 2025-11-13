import { StoreMeta } from './StoreMeta';

export interface UserSearchResponseItem {
  code: string;
  text: string;
  score: number;
  meta: StoreMeta;
}

export interface UserSearchResponse {
  items: UserSearchResponseItem[];
}
