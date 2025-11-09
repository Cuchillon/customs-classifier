interface StoreMeta {
  client: string;
  specification: string;
}

interface UserSearchResponseItem {
  code: string;
  text: string;
  score: number;
  meta: StoreMeta;
}

interface UserSearchResponse {
  items: UserSearchResponseItem[];
}
