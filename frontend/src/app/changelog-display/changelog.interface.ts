export interface Changelog {
  version: string;
  releaseDate: string;
  changes: Change[];
}

export interface Change {
  type: string;
  description: string;
  usNumber: string;
}
