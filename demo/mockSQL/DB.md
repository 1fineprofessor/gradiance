CREATE TABLE `Users` (
  `user_id` INT PRIMARY KEY AUTO_INCREMENT,
  `student_id` VARCHAR(20) UNIQUE,
  `first_name` VARCHAR(50),
  `middle_initial` CHAR(1),
  `last_name` VARCHAR(50),
  `email` VARCHAR(100) UNIQUE NOT NULL,
  `password_hash` VARCHAR(255) NOT NULL,
  `role` ENUM ('student', 'ta', 'instructor') NOT NULL,
  `admin_rights` INT NOT NULL
);

CREATE TABLE `Courses` (
  `course_id` INT PRIMARY KEY AUTO_INCREMENT,
  `course_name` VARCHAR(100) NOT NULL,
  `pre_reqs` TEXT,
  `created_by` INT NOT NULL
);

CREATE TABLE `Competencies` (
  `competency_id` INT PRIMARY KEY AUTO_INCREMENT,
  `course_id` INT NOT NULL,
  `competency_name` VARCHAR(100) NOT NULL,
  `description` TEXT,
  `semester` TEXT
);

CREATE TABLE `Enrollments` (
  `enrollment_id` INT PRIMARY KEY AUTO_INCREMENT,
  `student_id` VARCHAR(20) NOT NULL,
  `course_id` INT NOT NULL
);

CREATE TABLE `Questions` (
  `question_id` INT PRIMARY KEY AUTO_INCREMENT,
  `parent_question_id` INT DEFAULT null,
  `created_by` INT NOT NULL,
  `last_modified_by` INT,
  `created_at` TIMESTAMP DEFAULT (CURRENT_TIMESTAMP),
  `last_modified_at` TIMESTAMP DEFAULT (CURRENT_TIMESTAMP),
  `question_text` TEXT NOT NULL,
  `question_image_url` VARCHAR(255)
);

CREATE TABLE `QuestionUsages` (
  `usage_id` INT PRIMARY KEY AUTO_INCREMENT,
  `question_id` INT NOT NULL,
  `competency_id` INT NOT NULL,
  `question_notes` TEXT
);

CREATE TABLE `Interviews` (
  `interview_id` INT PRIMARY KEY AUTO_INCREMENT,
  `student_id` VARCHAR(20) NOT NULL,
  `course_id` INT NOT NULL,
  `conducted_at` TIMESTAMP DEFAULT (CURRENT_TIMESTAMP)
);

CREATE TABLE `QuestionsDuringInterview` (
  `assignment_id` INT PRIMARY KEY AUTO_INCREMENT,
  `interview_id` INT NOT NULL,
  `usage_id` INT NOT NULL,
  `assigned_at` TIMESTAMP DEFAULT (CURRENT_TIMESTAMP)
);

ALTER TABLE `Courses` ADD FOREIGN KEY (`created_by`) REFERENCES `Users` (`user_id`);

ALTER TABLE `Competencies` ADD FOREIGN KEY (`course_id`) REFERENCES `Courses` (`course_id`);

ALTER TABLE `Enrollments` ADD FOREIGN KEY (`student_id`) REFERENCES `Users` (`student_id`);

ALTER TABLE `Enrollments` ADD FOREIGN KEY (`course_id`) REFERENCES `Courses` (`course_id`);

ALTER TABLE `Questions` ADD FOREIGN KEY (`parent_question_id`) REFERENCES `Questions` (`question_id`);

ALTER TABLE `Questions` ADD FOREIGN KEY (`created_by`) REFERENCES `Users` (`user_id`);

ALTER TABLE `Questions` ADD FOREIGN KEY (`last_modified_by`) REFERENCES `Users` (`user_id`);

ALTER TABLE `QuestionUsages` ADD FOREIGN KEY (`question_id`) REFERENCES `Questions` (`question_id`);

ALTER TABLE `QuestionUsages` ADD FOREIGN KEY (`competency_id`) REFERENCES `Competencies` (`competency_id`);

ALTER TABLE `Interviews` ADD FOREIGN KEY (`student_id`) REFERENCES `Users` (`student_id`);

ALTER TABLE `Interviews` ADD FOREIGN KEY (`course_id`) REFERENCES `Courses` (`course_id`);

ALTER TABLE `QuestionsDuringInterview` ADD FOREIGN KEY (`interview_id`) REFERENCES `Interviews` (`interview_id`);

ALTER TABLE `QuestionsDuringInterview` ADD FOREIGN KEY (`usage_id`) REFERENCES `QuestionUsages` (`usage_id`);
