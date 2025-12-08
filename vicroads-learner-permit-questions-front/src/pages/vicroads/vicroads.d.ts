// @ts-ignore
/* eslint-disable */

declare namespace API_Vicroads {

  type Answer = {
    correct?: boolean;
    option?: string;
  };

  type Question = {
    id?: string;
    createTime?: string;
    updateTime?: string;

    title?: string;
    imageUrl?: string;
    imageBase64?: string;

    answers?: Answer[];
  };

}
