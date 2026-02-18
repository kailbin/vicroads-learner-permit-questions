import { useEffect } from 'react';
import { history } from '@umijs/max';
import { GA_ID } from '@/../config/GlobalConfig';

// 声明 gtag 函数
declare global {
  function gtag(...args: any[]): void;
}

const GA = () => {
  useEffect(() => {
    // 监听路由变化
    const unlisten = history.listen(({ location }) => {
      if (typeof gtag !== 'undefined') {
        gtag('config', GA_ID, {
          page_path: location.pathname + location.search,
        });
      }
    });

    // 初始页面视图
    if (typeof gtag !== 'undefined') {
      gtag('config', GA_ID, {
        page_path: history.location.pathname + history.location.search,
      });
    }

    return unlisten;
  }, []);

  return null;
};

export default GA;
