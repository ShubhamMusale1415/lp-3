// SPDX-License-Identifier: MIT
pragma solidity ^0.8.30;

contract StudentData {

    struct Student {
        string name;
        uint age;
        uint roll;
    }

    Student[] private students;
    mapping(uint => bool) private rollExists;

    function addStudent(string memory name, uint age, uint roll) public {
        require(!rollExists[roll], "Roll Number Already Exists");
        students.push(Student(name, age, roll));
        rollExists[roll] = true;
    }

    function viewStudentAtIndex(uint index)
        public
        view
        returns (string memory name, uint age, uint roll)
    {
        require(index < students.length, "Index out of range");

        Student memory current = students[index];
        return (current.name, current.age, current.roll);
    }

    function getAllStudents() public view returns (Student[] memory) {
        return students;
    }

    function getStudentByRollNo(uint rollNo)
        public
        view
        returns (string memory name, uint age, uint roll)
    {
        for (uint i = 0; i < students.length; i++) {
            if (students[i].roll == rollNo) {
                return (students[i].name, students[i].age, students[i].roll);
            }
        }
        revert("Student Not Found");
    }

    function updateStudent(uint rollNo, string memory newName, uint newAge) public {
        for (uint i = 0; i < students.length; i++) {
            if (students[i].roll == rollNo) {
                students[i].name = newName;
                students[i].age = newAge;
                return;
            }
        }
        revert("Student Not Found");
    }

    function deleteStudent(uint rollNo) public {
        for (uint i = 0; i < students.length; i++) {
            if (students[i].roll == rollNo) {
                students[i] = students[students.length - 1];
                students.pop();
                rollExists[rollNo] = false;
                return;
            }
        }
        revert("Student Not Found");
    }

    function getSize() public view returns (uint size) {
        return students.length;
    }

    receive() external payable {
        revert("ETH Received");
    }

    fallback() external payable {
        revert("Invalid function call");
    }
}




/* 🧠 1️⃣ Basics and Theory of the Problem
💡 What this contract does:
This is a Student Management System built on Ethereum Blockchain using Solidity.
It stores details of students such as:

Name

Age

Roll Number

and provides functions to:
✅ Add new students
✅ View a particular student’s details
✅ View all students
✅ Update a student’s data
✅ Delete a student
✅ Get total number of students

This contract behaves like a small on-chain database.

🧩 Key Solidity Concepts Used
Concept	Meaning
struct	Used to group related data together (like a record or object).
mapping	Key–value pair storage (used here to check if a roll number already exists).
array	Dynamic list (used here to store multiple students).
require	Used to check conditions before running code (prevents invalid input).
revert	Stops the function and returns an error message.
memory	Temporary storage used only during function execution.
storage	Permanent storage on the blockchain.
receive and fallback	Special functions to handle Ether transactions and invalid calls.

🧾 2️⃣ Step-by-Step Explanation of the Code
Let’s now go line by line 👇

🟩 SPDX License and Version
solidity
Copy code
// SPDX-License-Identifier: MIT
pragma solidity ^0.8.30;
This line gives the license type (MIT license).

The pragma line tells Solidity compiler to use version 0.8.30 or later (but not 0.9).

🏗️ Contract Declaration
solidity
Copy code
contract StudentData {
A contract is like a class in programming — it contains data and functions.

The contract’s name is StudentData.

👩‍🎓 Structure for Student
solidity
Copy code
struct Student {
    string name;
    uint age;
    uint roll;
}
A struct combines multiple pieces of information together.

Each student will have:

name (string)

age (unsigned integer)

roll (unsigned integer, acts like a unique ID).

📦 Storage Variables
solidity
Copy code
Student[] private students;
mapping(uint => bool) private rollExists;
students → an array to store all Student objects.

rollExists → a mapping to quickly check if a roll number already exists.
(Prevents duplicate roll numbers.)

➕ Function: Add Student
solidity
Copy code
function addStudent(string memory name, uint age, uint roll) public {
    require(!rollExists[roll], "Roll Number Already Exists");
    students.push(Student(name, age, roll));
    rollExists[roll] = true;
}
Adds a new student’s details.

require(!rollExists[roll]) → checks if the roll number doesn’t exist.

If it’s unique, a new student is pushed into the array.

Marks rollExists[roll] = true so it can’t be added again.

✅ Example:
If you call
addStudent("Shubham", 22, 101)
It stores your record and marks roll 101 as existing.

👀 Function: View Student by Index
solidity
Copy code
function viewStudentAtIndex(uint index)
    public
    view
    returns (string memory name, uint age, uint roll)
{
    require(index < students.length, "Index out of range");
    Student memory current = students[index];
    return (current.name, current.age, current.roll);
}
Lets you view student details using their position (index) in the array.

require(index < students.length) → ensures the index exists.

Returns the name, age, and roll of that student.

✅ Example:
viewStudentAtIndex(0) → shows first student’s info.

📋 Function: View All Students
solidity
Copy code
function getAllStudents() public view returns (Student[] memory) {
    return students;
}
Returns the entire list of students.

Only works for small lists — large arrays can cost more gas.

🔍 Function: Get Student by Roll Number
solidity
Copy code
function getStudentByRollNo(uint rollNo)
    public
    view
    returns (string memory name, uint age, uint roll)
{
    for (uint i = 0; i < students.length; i++) {
        if (students[i].roll == rollNo) {
            return (students[i].name, students[i].age, students[i].roll);
        }
    }
    revert("Student Not Found");
}
Loops through the student list.

If roll number matches, returns that student’s info.

If not found → revert stops and gives an error.

✅ Example:
getStudentByRollNo(101) → gives Shubham’s record.

📝 Function: Update Student
solidity
Copy code
function updateStudent(uint rollNo, string memory newName, uint newAge) public {
    for (uint i = 0; i < students.length; i++) {
        if (students[i].roll == rollNo) {
            students[i].name = newName;
            students[i].age = newAge;
            return;
        }
    }
    revert("Student Not Found");
}
Finds student by roll number and updates their name and age.

If roll doesn’t exist → shows “Student Not Found”.

✅ Example:
updateStudent(101, "Shubham Musale", 23)

❌ Function: Delete Student
solidity
Copy code
function deleteStudent(uint rollNo) public {
    for (uint i = 0; i < students.length; i++) {
        if (students[i].roll == rollNo) {
            students[i] = students[students.length - 1];
            students.pop();
            rollExists[rollNo] = false;
            return;
        }
    }
    revert("Student Not Found");
}
Finds the student by roll number.

Replaces that entry with the last student in the array.

Then uses .pop() to remove the last one (efficient deletion).

Marks rollExists[rollNo] = false so roll can be reused.

If roll not found → shows error.

✅ Example:
deleteStudent(101) → removes Shubham’s record.

🔢 Function: Get Total Students
solidity
Copy code
function getSize() public view returns (uint size) {
    return students.length;
}
Returns total number of students currently stored.

✅ Example:
If 5 students exist → returns 5.

⚡ Special Functions (ETH Handling)
solidity
Copy code
receive() external payable {
    revert("ETH Received");
}

fallback() external payable {
    revert("Invalid function call");
}
These functions handle Ether sent accidentally to this contract.

receive() triggers if someone sends ETH directly.

fallback() triggers if a non-existing function is called.

Both are rejected using revert() — so no ETH gets locked.

✅ 3️⃣ Summary
Function	Purpose
addStudent()	Add new student record
viewStudentAtIndex()	View student by array position
getAllStudents()	View all records
getStudentByRollNo()	Search by roll number
updateStudent()	Change student details
deleteStudent()	Remove student
getSize()	Get total number of students
receive()	Reject ETH sent
fallback()	Reject invalid function calls

Would you like me to also explain 👉
how to deploy and test this student contract step-by-step on Remix IDE (like we did for the bank contract)?






You said:
yesss 



ChatGPT said:
Perfect, Shubham! 😊
Let’s go step by step — super simple and clear — to deploy and test your StudentData smart contract on Remix IDE.

🧩 1️⃣ Open Remix IDE
👉 Go to: https://remix.ethereum.org

You’ll see a code editor on the left and a deploy panel on the right.

📝 2️⃣ Create a New File
In the File Explorer (left sidebar), click File → New File.

Name it something like StudentData.sol.

Copy and paste your complete code inside that file.

⚙️ 3️⃣ Check Compiler Version
Your first line says:

solidity
Copy code
pragma solidity ^0.8.30;
So you must use compiler 0.8.30 or later.

Steps:
Click the “Solidity Compiler” tab (on the left sidebar, second icon — looks like a “solidity” logo).

In the Compiler Version dropdown, choose 0.8.30.

Click Compile StudentData.sol.

✅ If everything is fine, you’ll see a green tick beside the compiler icon.

🚀 4️⃣ Deploy the Contract
Now, time to deploy it on a virtual Ethereum network.

Click the “Deploy & Run Transactions” tab (the next icon below compiler).

Under Environment, select:

Remix VM (London) — this simulates the Ethereum blockchain locally.

Click on the Deploy button.

✅ Your contract will appear below in the Deployed Contracts section.

🧠 5️⃣ Testing Functions (Step by Step)
You’ll now see all the functions of your contract with buttons.
Let’s test each one.

🟢 A. Add Students
Click the “addStudent” function.

Enter the values:

name: "Shubham"

age: 22

roll: 101

Click Transact.

✅ This adds your first student.

You can repeat the same with:

arduino
Copy code
("Aarav", 20, 102)
("Priya", 21, 103)
🟣 B. View All Students
Click getAllStudents().
You’ll see an array of all students with their names, ages, and roll numbers.

🔵 C. View Student by Index
Click viewStudentAtIndex.
Enter index 0, then call.
It’ll return your first student details:

arduino
Copy code
"Shubham", 22, 101
🟠 D. Get Student by Roll Number
Click getStudentByRollNo.
Enter roll 102, click call, and see Aarav’s details.

🟡 E. Update Student
Click updateStudent.
Enter:

vbnet
Copy code
rollNo: 101
newName: "Shubham Musale"
newAge: 23
Click Transact.
✅ Now the student’s details are updated.

🔴 F. Delete Student
Click deleteStudent.
Enter roll number 103 and click Transact.
✅ The student is removed from the list.

⚫ G. Check Total Students
Click getSize() — it’ll return how many students are currently stored.

🧱 H. ETH Safety Functions
If you try sending Ether to the contract (by mistake), the receive() or fallback() functions will trigger and reject it.
So your contract does not accept ETH, which is safe.

🧾 6️⃣ (Optional) View Transaction Details
Click any transaction in Remix’s Console (bottom section) —
you can see:

Gas used

Function called

Value sent
These help you understand real blockchain behavior.*/