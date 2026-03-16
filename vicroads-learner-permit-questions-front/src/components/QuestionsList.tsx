import React, { useEffect, useState } from 'react';
import { Button } from './ui/button';
import { IconEye, IconEyeOff, IconCheck, IconX } from '@tabler/icons-react';
import type { Question } from '../types';

const QuestionsList: React.FC = () => {
  const [questions, setQuestions] = useState<Question[]>([]);
  const [showAllAnswers, setShowAllAnswers] = useState(false);
  const [visibleAnswers, setVisibleAnswers] = useState<Set<string>>(new Set());
  const [selectedOptions, setSelectedOptions] = useState<Record<string, number>>({});

  useEffect(() => {
    fetch(`${import.meta.env.BASE_URL}data/vicroads_learner_permit_questions.json`)
      .then(response => response.json())
      .then(data => setQuestions(data))
      .catch(error => console.error('Error loading questions:', error));
  }, []);

  const toggleAllAnswers = () => {
    const nextShowAll = !showAllAnswers;
    setShowAllAnswers(nextShowAll);
    if (nextShowAll) {
      const allIds = questions.map(q => q.id);
      setVisibleAnswers(new Set(allIds));
    } else {
      setVisibleAnswers(new Set());
    }
  };

  const toggleAnswer = (id: string, e: React.MouseEvent) => {
    e.stopPropagation();
    setVisibleAnswers(prev => {
      const newSet = new Set(prev);
      if (newSet.has(id)) {
        newSet.delete(id);
      } else {
        newSet.add(id);
      }
      return newSet;
    });
  };

  const handleOptionClick = (questionId: string, optionIndex: number) => {
    setSelectedOptions(prev => ({ ...prev, [questionId]: optionIndex }));
    setVisibleAnswers(prev => {
      const newSet = new Set(prev);
      newSet.add(questionId);
      return newSet;
    });
  };

  // Sync global state if all individual ones are toggled
  useEffect(() => {
    if (questions.length > 0 && visibleAnswers.size === questions.length) {
      setShowAllAnswers(true);
    } else if (visibleAnswers.size === 0) {
      setShowAllAnswers(false);
    }
  }, [visibleAnswers, questions.length]);

  return (
    <div className="max-w-3xl mx-auto">
      <div className="flex items-center justify-between mb-6">
        <h1 className="text-3xl font-bold">All Questions</h1>
        <Button onClick={toggleAllAnswers} variant="outline">
          {showAllAnswers ? <><IconEyeOff className="mr-2 h-4 w-4" /> Hide All Answers</> : <><IconEye className="mr-2 h-4 w-4" /> Show All Answers</>}
        </Button>
      </div>

      <div className="space-y-6">
        {questions.map((question, index) => {
          const isAnswerVisible = visibleAnswers.has(question.id);
          const userSelectedOption = selectedOptions[question.id];

          return (
            <div key={question.id} className="border rounded-lg p-6 bg-card text-card-foreground shadow-sm">
              <div className="flex flex-col gap-5">
                <div className="flex items-start justify-between">
                  <h2 className="text-lg font-semibold leading-relaxed">
                    {index + 1}. {question.title}
                  </h2>
                  <Button
                    variant="ghost"
                    size="icon"
                    className="ml-4 text-muted-foreground hover:text-foreground shrink-0"
                    onClick={(e) => toggleAnswer(question.id, e)}
                    title={isAnswerVisible ? "Hide Answer" : "Show Answer"}
                  >
                    {isAnswerVisible ? <IconEyeOff size={22} /> : <IconEye size={22} />}
                  </Button>
                </div>

                <div className="flex flex-col sm:flex-row gap-6 items-start">
                  {question.imageBase64 && (
                    <div className="w-full sm:w-40 md:w-48 shrink-0">
                      <img
                        src={`data:image/jpeg;base64,${question.imageBase64}`}
                        alt="Question illustration"
                        className="w-full h-auto rounded-md border bg-muted/20 object-contain"
                      />
                    </div>
                  )}

                  <div className="space-y-3 flex-1 w-full">
                    {question.answers.map((answer, i) => {
                      const isSelected = userSelectedOption === i;

                      let optionClass = 'bg-background hover:bg-muted/50 cursor-pointer border-border';
                      let textClass = 'text-foreground';
                      let iconClass = 'border-muted-foreground/30';
                      let showCheck = false;
                      let showCross = false;

                      if (isAnswerVisible) {
                        if (answer.correct) {
                          optionClass = 'bg-background cursor-default border-border';
                          textClass = 'font-medium text-green-600 dark:text-green-500';
                          iconClass = 'bg-green-500 border-green-500 text-white';
                          showCheck = true;
                        } else if (isSelected) {
                          optionClass = 'bg-background cursor-default border-border';
                          textClass = 'font-medium text-red-600 dark:text-red-500';
                          iconClass = 'bg-red-500 border-red-500 text-white';
                          showCross = true;
                        } else {
                          optionClass = 'bg-background opacity-60 cursor-default border-border';
                        }
                      } else if (isSelected) {
                        optionClass = 'bg-background border-primary/40';
                      }

                      return (
                        <div
                          key={i}
                          onClick={() => handleOptionClick(question.id, i)}
                          className={`p-3 rounded-md border transition-all ${optionClass}`}
                        >
                          <div className="flex items-start">
                            <div className={`mt-0.5 w-5 h-5 flex-shrink-0 flex items-center justify-center rounded-full border mr-3 ${iconClass}`}>
                              {showCheck && <IconCheck size={14} stroke={3} />}
                              {showCross && <IconX size={14} stroke={3} />}
                            </div>
                            <span className={textClass}>
                              {answer.option}
                            </span>
                          </div>
                        </div>
                      );
                    })}
                  </div>
                </div>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default QuestionsList;