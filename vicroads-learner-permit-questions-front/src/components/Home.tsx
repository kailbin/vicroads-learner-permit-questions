import React from 'react';
import { Link } from 'react-router-dom';

const Home: React.FC = () => {
  return (
    <div className="max-w-4xl mx-auto">
      <div className="text-center mb-8">
        <h1 className="text-4xl font-bold mb-4">Victoria Learner Permit Test Practice</h1>
        <p className="text-xl text-muted-foreground">Prepare for your driving test and master all necessary knowledge</p>
      </div>

      <div className="grid md:grid-cols-2 gap-6 mb-8">
        <div className="border rounded-lg p-6">
          <h2 className="text-2xl font-semibold mb-2">Test Overview</h2>
          <p className="text-muted-foreground mb-4">Learn basic information about the test</p>
          <p className="mb-4">This practice system contains <strong>2</strong> official Victoria Learner Permit test questions.</p>
          <p>These questions cover important aspects of road safety, traffic rules, and driving knowledge.</p>
        </div>

        <div className="border rounded-lg p-6">
          <h2 className="text-2xl font-semibold mb-2">Test Rules</h2>
          <p className="text-muted-foreground mb-4">Important test tips</p>
          <ul className="list-disc list-inside space-y-2">
            <li>Read each question carefully</li>
            <li>Review related images (if provided)</li>
            <li>Select the most accurate answer</li>
            <li>Some questions may have multiple correct answers</li>
            <li>Practice helps improve passing rates</li>
          </ul>
        </div>
      </div>

      <div className="border rounded-lg p-6 bg-card text-center mb-8">
        <h2 className="text-2xl font-semibold mb-4">Detailed Process Guide</h2>
        <p className="text-muted-foreground mb-4">For the detailed process, please check the following public account link:</p>
        <a
          href="https://mp.weixin.qq.com/s/tyVcmXPU8ah9EwFlWtd7bA"
          target="_blank"
          rel="noopener noreferrer"
          className="text-primary hover:underline font-medium text-lg block mb-6 transition-colors"
        >
          《拿到了澳洲驾照》
        </a>

        <div className="flex flex-col items-center">
          <img
            src={`${import.meta.env.BASE_URL}images/mp-zxz.png`}
            alt="WeChat Public Account"
            className="w-480 h-48 object-contain"
          />
        </div>
      </div>

      <div className="text-center">
        <p className="text-lg mb-4">Start practicing and get ready for the real test!</p>
        <Link
          to="/questions"
          className="inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 bg-primary text-primary-foreground hover:bg-primary/90 h-10 px-4 py-2"
        >
          Start Practice
        </Link>
      </div>
    </div>
  );
};

export default Home;