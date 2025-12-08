// @ts-ignore
/* eslint-disable */
import { request } from "@umijs/max";

export async function getAllQuestions() {
  const data = await request<API_Vicroads.Question[]>(
    `/data/vicroads_learner_permit_questions.json`,
    {
      method: "GET",
    }
  );

  console.log('API 响应数据:', data, '类型:', Array.isArray(data) ? '数组' : typeof data);

  // useRequest 期望返回的对象具有 data 属性
  // 因为响应已经是数组了，需要包装成 { data: [...] } 结构
  return { data };
}
