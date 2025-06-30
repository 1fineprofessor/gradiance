
document.addEventListener('DOMContentLoaded', function () {
    const students = [
        {
            name: "Alice",
            course: "Intro",
            topic: "Recursion",
            placeInQueue: 1,
            other: "more info"
        },
        {
            name: "Bob",
            course: "Software",
            topic: "Integration by Parts",
            placeInQueue: 2,
            other: "more info"
        },
        {
            name: "Charlie",
            course: "Comp Sys",
            topic: "C+",
            placeInQueue: 3,
            other: "more info"
        }
    ];

    const queueElement = document.getElementById("studentQueue");

        if (queueElement) {
            queueElement.innerHTML = "";

            students.forEach((student, index) => {
                const studentCard = document.createElement("div");
                studentCard.classList.add("card", "mb-2", "p-2", "student-card");
                studentCard.setAttribute("data-index", index);

                studentCard.innerHTML = `
                    <div class="row">
                        <!-- Left: Place in Queue -->
                        <div class="col-2 d-flex align-items-center justify-content-center fw-bold">
                        ${student.placeInQueue}
                        </div>

                        <!-- Right: Name, Course, and Topic -->
                        <div class="col-10">
                        <div class="row">
                            <div class="col-12 fw-bold text-end">
                            ${student.name}
                            </div>
                            <div class="col-12 text-end text-muted small">
                            ${student.course} – ${student.topic}
                            </div>
                        </div>
                        </div>
                    </div>
                    `;

                queueElement.appendChild(studentCard);
            });
        }

    document.addEventListener('click', function (e) {
        const clickedCard = e.target.closest('.student-card');

        if (clickedCard) {
            // Remove highlight from all cards
            document.querySelectorAll('.student-card').forEach(card => {
                card.classList.remove('border-primary', 'border', 'shadow');
            });

            // Highlight the clicked card
            clickedCard.classList.add('border', 'border-primary', 'shadow');

            // Get student info
            const index = clickedCard.getAttribute('data-index');
            const student = students[index];
            console.log("Selected student:", student.name);  // Debug output

            // Make sure the "Student Info" tab is active
            const studentInfoTab = new bootstrap.Tab(document.querySelector('#student-info-tab'));
            studentInfoTab.show();

            // Update student info section
            const studentDetails = document.getElementById("studentDetails");
            if (studentDetails) {
                studentDetails.innerHTML = `
                    <div class="container-fluid">
                        <div class="row mb-1">
                            <div class="col-1"><strong>Name:</strong></div>
                            <div class="col-2 text-end">${student.name}</div>
                        
                            <div class="col-1"><strong>Course:</strong></div>
                            <div class="col-2 text-end">${student.course}</div>
                        
                            <div class="col-1"><strong>Topic:</strong></div>
                            <div class="col-2 text-end">${student.topic}</div>
                            <div class="col-1"><strong>Other:</strong></div>
                            <div class="col-2 text-end">${student.other}</div>
                        
                    </div>
                `;


            }
        }
    });

});


/**
 * STUDENT VIEW ////////////////////////
 */

