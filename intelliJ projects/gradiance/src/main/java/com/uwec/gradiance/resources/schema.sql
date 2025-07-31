DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'users_role_enum') THEN
        CREATE TYPE users_role_enum AS ENUM ('student','ta','instructor');
    END IF;
END$$;

CREATE TABLE IF NOT EXISTS users (
    user_id SERIAL PRIMARY KEY,
    student_id VARCHAR(20) UNIQUE,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role users_role_enum NOT NULL,
    admin_rights INT NOT NULL
);

CREATE TABLE IF NOT EXISTS courses (
    course_id SERIAL PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL,
    pre_reqs TEXT,
    created_by INT NOT NULL REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS competencies (
    competency_id SERIAL PRIMARY KEY,
    course_id INT NOT NULL REFERENCES courses(course_id),
    competency_name VARCHAR(100) NOT NULL,
    description TEXT,
    semester TEXT
);

CREATE TABLE IF NOT EXISTS enrollments (
    enrollment_id SERIAL PRIMARY KEY,
    student_id VARCHAR(20) NOT NULL REFERENCES users(student_id),
    course_id INT NOT NULL REFERENCES courses(course_id)
);

CREATE TABLE IF NOT EXISTS questions (
    id SERIAL PRIMARY KEY,
    parent_question_id INT REFERENCES questions(id),
    created_by INT NOT NULL REFERENCES users(user_id),
    last_modified_by INT REFERENCES users(user_id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    question_text TEXT NOT NULL,
    question_image_url VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS questionusages (
    usage_id SERIAL PRIMARY KEY,
    question_id INT NOT NULL REFERENCES questions(id),
    competency_id INT NOT NULL REFERENCES competencies(competency_id),
    question_notes TEXT
);

CREATE TABLE IF NOT EXISTS interviews (
    interview_id SERIAL PRIMARY KEY,
    student_id VARCHAR(20) NOT NULL REFERENCES users(student_id),
    course_id INT NOT NULL REFERENCES courses(course_id),
    conducted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS questionsduringinterview (
    assignment_id SERIAL PRIMARY KEY,
    interview_id INT NOT NULL REFERENCES interviews(interview_id),
    usage_id INT NOT NULL REFERENCES questionusages(usage_id),
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);