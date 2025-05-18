"use client";

import React, { useState } from "react";
import QuizManager from "../components/elearning/QuizManager";

export default function Home() {
  const [activeTab, setActiveTab] = useState("quizzes");

  return (
    <div className="min-h-screen bg-white text-black font-sans p-8">
      <header className="mb-8">
        <h1 className="text-4xl font-bold">E-Learning App</h1>
      </header>

      <nav className="mb-8 flex space-x-4 border-b border-gray-300">
        <button
          className={`pb-2 ${
            activeTab === "quizzes" ? "border-b-2 border-black font-semibold" : ""
          }`}
          onClick={() => setActiveTab("quizzes")}
        >
          Quizzes
        </button>
        <button
          className={`pb-2 ${
            activeTab === "assignments" ? "border-b-2 border-black font-semibold" : ""
          }`}
          onClick={() => setActiveTab("assignments")}
        >
          Assignments
        </button>
        <button
          className={`pb-2 ${
            activeTab === "grades" ? "border-b-2 border-black font-semibold" : ""
          }`}
          onClick={() => setActiveTab("grades")}
        >
          Grades
        </button>
      </nav>

      <main>
        {activeTab === "quizzes" && <QuizManager />}
        {activeTab === "assignments" && <div>Assignment management coming soon...</div>}
        {activeTab === "grades" && <div>Grade management coming soon...</div>}
      </main>
    </div>
  );
}
