export interface Answer {
  correct: boolean;
  option: string;
}

export interface Question {
  answers: Answer[];
  createTime: string;
  id: string;
  imageBase64: string;
  imageUrl: string;
  title: string;
  updateTime: string;
}