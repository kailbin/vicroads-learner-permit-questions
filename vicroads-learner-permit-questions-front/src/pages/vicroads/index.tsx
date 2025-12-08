import { GridContent } from '@ant-design/pro-components';
import { Card, Checkbox, Divider, Empty, Image, Spin, Space, Row, Col } from 'antd';
import useStyles from './index.style';
import { getAllQuestions } from './vicroads.api';
import { useRequest } from '@umijs/max';

export default () => {
  const { data: questions = [], loading } = useRequest(() => {
    return getAllQuestions();
  });

  if (loading) {
    return (
      <GridContent>
        <Card variant="borderless">
          <Spin size="large" tip="加载题目中..." />
        </Card>
      </GridContent>
    );
  }

  if (!questions || questions.length === 0) {
    return (
      <GridContent>
        <Card variant="borderless">
          <Empty description="暂无题目数据" />
        </Card>
      </GridContent>
    );
  }

  return (
    <GridContent>
      <Card variant="borderless">
        <h1>维州驾照学习者许可证练习题 (共 {questions.length} 道)</h1>
        <Divider />

        <Space direction="vertical" style={{ width: '100%' }} size="large">
          {questions.map((question, index) => {
            return (
              <Card key={question.id} type="inner" title={`第 ${index + 1} 题`}>
                {/* 题目标题 */}
                <div style={{ marginBottom: '16px', fontSize: '16px', fontWeight: 'bold' }}>
                  {question.title}
                </div>

                {/* 图片在左，选项在右 */}
                <Row gutter={[16, 0]}>
                  {/* 左侧：图片 */}
                  {question.imageBase64 && (
                    <Col xs={24} sm={24} md={12}>
                      <Image
                        src={`data:image/jpeg;base64,${question.imageBase64}`}
                        alt={question.title}
                        style={{ width: '100%', maxHeight: '400px' }}
                        preview
                      />
                    </Col>
                  )}

                  {/* 右侧：选项 */}
                  <Col xs={24} sm={24} md={question.imageBase64 ? 12 : 24}>
                    <Space direction="vertical" style={{ width: '100%' }}>
                      {question.answers?.map((answer, answerIndex) => (
                        <div key={answerIndex}>
                          <Checkbox
                            checked={answer.correct}
                            disabled
                          >
                            <span style={{
                              color: answer.correct ? '#52c41a' : 'inherit',
                              fontWeight: answer.correct ? 'bold' : 'normal'
                            }}>
                              {answer.option}
                              {answer.correct && ' ✓'}
                            </span>
                          </Checkbox>
                        </div>
                      ))}
                    </Space>
                  </Col>
                </Row>
              </Card>
            );
          })}
        </Space>
      </Card>
    </GridContent>
  );
};
