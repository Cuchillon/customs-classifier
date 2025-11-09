export interface UserSearchRequestMeta {
  clients: string[];
  specifications: string[];
}

export interface UserSearchRequest {
  query: string;
  topK: number;
  similarityThreshold: number;
  meta?: UserSearchRequestMeta
}
