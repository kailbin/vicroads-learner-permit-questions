import React from 'react';

const About: React.FC = () => {
  return (
    <div className="max-w-4xl mx-auto">
      <h1 className="text-3xl font-bold mb-6">About Us</h1>

      <div className="space-y-6">
        <div className="border rounded-lg p-6">
          <h2 className="text-2xl font-semibold mb-4">VicRoads Practice Test</h2>
          <p className="text-muted-foreground mb-4">
            This is an online practice system designed for the Victoria, Australia Learner Permit Test.
          </p>
          <p>
            Our goal is to help learners familiarize themselves with the test content through real exam questions and improve their passing rate.
          </p>
        </div>

        <div className="border rounded-lg p-6">
          <h2 className="text-2xl font-semibold mb-4">Features</h2>
          <ul className="list-disc list-inside space-y-2">
            <li>Includes official test questions</li>
            <li>Detailed answer explanations</li>
            <li>Image support</li>
            <li>Real-time answer checking</li>
            <li>Responsive design for mobile devices</li>
          </ul>
        </div>

        <div className="border rounded-lg p-6">
          <h2 className="text-2xl font-semibold mb-4">Future Plans</h2>
          <ul className="list-disc list-inside space-y-2">
            <li>Incorrect questions review</li>
            <li>Real exam simulation</li>
            <li>Progress tracking</li>
            <li>Learning statistics</li>
            <li>More question updates</li>
          </ul>
        </div>

        <div className="border rounded-lg p-6">
          <h2 className="text-2xl font-semibold mb-4">Disclaimer</h2>
          <p className="text-sm text-muted-foreground">
            This practice system is for educational reference only and cannot replace the official test. Actual test content may vary, please refer to official VicRoads information.
          </p>
        </div>
      </div>
    </div>
  );
};

export default About;