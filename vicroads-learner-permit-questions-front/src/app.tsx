import { BrowserRouter as Router, Routes, Route, Link, useLocation } from 'react-router-dom';
import { Button } from './components/ui/button';
import Home from './components/Home';
import QuestionsList from './components/QuestionsList';
import About from './components/About';
import ReactGA from 'react-ga4';
import { useEffect } from 'react';

ReactGA.initialize('G-PBHFL317TX');

function AnalyticsTracker() {
  const location = useLocation();
  useEffect(() => {
    ReactGA.send({ hitType: 'pageview', page: location.pathname + location.search });
  }, [location]);
  return null;
}

function App() {
  return (
    <Router basename={import.meta.env.BASE_URL}>
      <AnalyticsTracker />
      <div className="min-h-screen bg-background">
        <header className="border-b">
          <div className="container mx-auto px-4 py-4">
            <nav className="flex items-center justify-between">
              <div className="flex items-center space-x-4">
                <Link to="/" className="text-xl font-bold">VicRoads Practice</Link>
              </div>
              <div className="flex items-center space-x-4">
                <Link to="/">
                  <Button variant="ghost">Home</Button>
                </Link>
                <Link to="/questions">
                  <Button variant="ghost">Questions</Button>
                </Link>
                {/* <Link to="/about">
                  <Button variant="ghost">About</Button>
                </Link> */}
              </div>
            </nav>
          </div>
        </header>
        <main className="container mx-auto px-4 py-8">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/questions" element={<QuestionsList />} />
            <Route path="/about" element={<About />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export { App };
