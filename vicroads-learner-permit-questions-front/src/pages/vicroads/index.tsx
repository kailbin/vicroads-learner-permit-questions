import { DingdingOutlined } from '@ant-design/icons';
import { GridContent } from '@ant-design/pro-components';
import { Button, Card, Descriptions, Result, Steps } from 'antd';
import useStyles from './index.style';

const { Step } = Steps;

export default () => {
  const { styles } = useStyles();

  return (
    <GridContent>
      <Card variant="borderless">
        <Result
          status="success"
          title="提交成功"
          subTitle="subTitle。"
          style={{
            marginBottom: 16,
          }}
        >
          xxx
        </Result>
      </Card>
    </GridContent>
  );
};
