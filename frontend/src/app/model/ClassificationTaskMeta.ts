import { UserSearchRequestMeta } from './UserSearchRequestMeta';

export interface ClassificationTaskMeta {
  topK: number;
  similarityThreshold: number;
  meta?: UserSearchRequestMeta;
}
