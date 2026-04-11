export interface ClassificationTaskStatusResponse {
  id: number;
  status: 'STARTED' | 'COMPLETED' | 'DONE' | 'ERROR'
}
