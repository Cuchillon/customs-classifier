export interface StoreMeta {
  client: string;
  specification: string;
}

export interface UserSearchResponseItem {
  code: string;
  text: string;
  score: number;
  meta: StoreMeta;
}

export interface UserSearchResponse {
  items: UserSearchResponseItem[];
}
