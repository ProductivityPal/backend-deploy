// Define an array of tasks
const tasks = [
    {
        id: 1,
        name: 'Rozprochy - corba',
        deadline: '2023-04-20',
        difficulty: 3,
        timeEstimate: 4,
        likeliness: 2
    },
    {
        id: 2,
        name: 'Rozprochy - ray',
        deadline: '2023-05-01',
        difficulty: 3,
        timeEstimate: 5,
        likeliness: 2
    },
    {
        id: 3,
        name: 'IO - profil',
        deadline: '2023-04-17',
        difficulty: 3,
        timeEstimate: 4,
        likeliness: 4
    },
    {
        id: 4,
        name: 'Kalendarz - inz',
        deadline: '2023-04-22',
        difficulty: 5,
        timeEstimate: 6,
        likeliness: 5
    }
];

const weights = {
    deadline: 2,
    difficulty: 3,
    timeEstimate: 1,
    likeliness: 2
};

const energyLevelMultipliers = {
    low: 0.5,
    medium: 1,
    high: 1.5
};

// Get the user's energy level from input
const energyLevel = 'medium';

// Calculate the priority score for each task and add it as a new property
tasks.forEach(task => {
    const now = new Date();
    const daysUntilDeadline = Math.max(0, Math.ceil((new Date(task.deadline) - now) / (1000 * 60 * 60 * 24)));
    const priorityScore = (
        (weights.deadline * (1 - daysUntilDeadline / 7)) +
        (weights.difficulty * task.difficulty) +
        (weights.timeEstimate * task.timeEstimate) +
        (weights.likeliness * task.likeliness)
    );
    task.priorityScore = priorityScore;
});

// Sort the tasks by priority score in descending order
const sortedTasks = tasks.sort((a, b) => b.priorityScore - a.priorityScore);

// Suggest tasks based on user's energy level
const suggestedTasks = sortedTasks.filter(task => {
    if (energyLevel === 'low') {
        return task.difficulty <= 3 || task.likeliness >= 4;
    } else if (energyLevel === 'medium') {
        return task.difficulty <= 4 || task.likeliness >= 3;
    } else {
        return true;
    }
});

console.log(suggestedTasks);