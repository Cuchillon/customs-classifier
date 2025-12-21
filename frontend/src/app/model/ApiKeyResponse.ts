export interface ApiKeyResponse {
  id: number;
  keyId: string;
  scopes: string[];
  createdAt: string;
  expiresAt: string;
  active: boolean;
  rawKey: string;
}
