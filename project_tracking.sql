--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: histories; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE histories (
    id integer NOT NULL,
    task_id integer,
    change_type character varying,
    previous_condition character varying(30),
    current_condition character varying(30),
    created_date timestamp(0) without time zone DEFAULT now()
);


ALTER TABLE histories OWNER TO "Guest";

--
-- Name: histories_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE histories_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE histories_id_seq OWNER TO "Guest";

--
-- Name: histories_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE histories_id_seq OWNED BY histories.id;


--
-- Name: messages; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE messages (
    id integer NOT NULL,
    description text,
    user_id integer,
    task_id integer,
    date_created timestamp without time zone DEFAULT now()
);


ALTER TABLE messages OWNER TO "Guest";

--
-- Name: messages_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE messages_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE messages_id_seq OWNER TO "Guest";

--
-- Name: messages_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE messages_id_seq OWNED BY messages.id;


--
-- Name: roles; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE roles (
    id integer NOT NULL,
    name character varying
);


ALTER TABLE roles OWNER TO "Guest";

--
-- Name: role_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE role_id_seq OWNER TO "Guest";

--
-- Name: role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE role_id_seq OWNED BY roles.id;


--
-- Name: status; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE status (
    id integer NOT NULL,
    status character varying
);


ALTER TABLE status OWNER TO "Guest";

--
-- Name: status_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE status_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE status_id_seq OWNER TO "Guest";

--
-- Name: status_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE status_id_seq OWNED BY status.id;


--
-- Name: tasks; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE tasks (
    id integer NOT NULL,
    title character varying,
    creator_user_id integer,
    description text,
    type_task_id integer,
    developer_id integer,
    status_id integer,
    date_created timestamp(0) without time zone DEFAULT now()
);


ALTER TABLE tasks OWNER TO "Guest";

--
-- Name: tasks_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE tasks_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tasks_id_seq OWNER TO "Guest";

--
-- Name: tasks_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE tasks_id_seq OWNED BY tasks.id;


--
-- Name: tasks_messages; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE tasks_messages (
    id integer NOT NULL,
    task_id integer,
    message_id integer
);


ALTER TABLE tasks_messages OWNER TO "Guest";

--
-- Name: tasks_messages_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE tasks_messages_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tasks_messages_id_seq OWNER TO "Guest";

--
-- Name: tasks_messages_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE tasks_messages_id_seq OWNED BY tasks_messages.id;


--
-- Name: tasks_relationships; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE tasks_relationships (
    id integer NOT NULL,
    main_task_id integer,
    subtask_id integer
);


ALTER TABLE tasks_relationships OWNER TO "Guest";

--
-- Name: tasks_relationships_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE tasks_relationships_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tasks_relationships_id_seq OWNER TO "Guest";

--
-- Name: tasks_relationships_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE tasks_relationships_id_seq OWNED BY tasks_relationships.id;


--
-- Name: type_task; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE type_task (
    id integer NOT NULL,
    name character varying
);


ALTER TABLE type_task OWNER TO "Guest";

--
-- Name: type_task_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE type_task_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE type_task_id_seq OWNER TO "Guest";

--
-- Name: type_task_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE type_task_id_seq OWNED BY type_task.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE users (
    id integer NOT NULL,
    name character varying,
    email character varying,
    role_id integer
);


ALTER TABLE users OWNER TO "Guest";

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_id_seq OWNER TO "Guest";

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY histories ALTER COLUMN id SET DEFAULT nextval('histories_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY messages ALTER COLUMN id SET DEFAULT nextval('messages_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY roles ALTER COLUMN id SET DEFAULT nextval('role_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY status ALTER COLUMN id SET DEFAULT nextval('status_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY tasks ALTER COLUMN id SET DEFAULT nextval('tasks_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY tasks_messages ALTER COLUMN id SET DEFAULT nextval('tasks_messages_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY tasks_relationships ALTER COLUMN id SET DEFAULT nextval('tasks_relationships_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY type_task ALTER COLUMN id SET DEFAULT nextval('type_task_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- Data for Name: histories; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY histories (id, task_id, change_type, previous_condition, current_condition, created_date) FROM stdin;
1	2	change status	To Do	Done	2016-02-03 08:08:14
2	1	\N	\N	\N	2016-02-03 08:08:37
\.


--
-- Name: histories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('histories_id_seq', 2, true);


--
-- Data for Name: messages; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY messages (id, description, user_id, task_id, date_created) FROM stdin;
\.


--
-- Name: messages_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('messages_id_seq', 1, false);


--
-- Name: role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('role_id_seq', 2, true);


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY roles (id, name) FROM stdin;
1	PM
2	Developer
\.


--
-- Data for Name: status; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY status (id, status) FROM stdin;
1	To Do
2	In Progress
3	Test
4	Done
\.


--
-- Name: status_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('status_id_seq', 4, true);


--
-- Data for Name: tasks; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY tasks (id, title, creator_user_id, description, type_task_id, developer_id, status_id, date_created) FROM stdin;
2	ads	1	ttttt	1	1	1	2016-02-03 08:23:06
3	sadasdadsad	\N	\N	\N	\N	\N	2016-02-03 11:15:16
\.


--
-- Name: tasks_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('tasks_id_seq', 3, true);


--
-- Data for Name: tasks_messages; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY tasks_messages (id, task_id, message_id) FROM stdin;
\.


--
-- Name: tasks_messages_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('tasks_messages_id_seq', 1, false);


--
-- Data for Name: tasks_relationships; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY tasks_relationships (id, main_task_id, subtask_id) FROM stdin;
\.


--
-- Name: tasks_relationships_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('tasks_relationships_id_seq', 1, false);


--
-- Data for Name: type_task; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY type_task (id, name) FROM stdin;
1	Story
2	Epic
3	Task
4	Bug
\.


--
-- Name: type_task_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('type_task_id_seq', 4, true);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY users (id, name, email, role_id) FROM stdin;
\.


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('users_id_seq', 1, false);


--
-- Name: histories_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY histories
    ADD CONSTRAINT histories_pkey PRIMARY KEY (id);


--
-- Name: messages_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY messages
    ADD CONSTRAINT messages_pkey PRIMARY KEY (id);


--
-- Name: role_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY roles
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- Name: status_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY status
    ADD CONSTRAINT status_pkey PRIMARY KEY (id);


--
-- Name: tasks_messages_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY tasks_messages
    ADD CONSTRAINT tasks_messages_pkey PRIMARY KEY (id);


--
-- Name: tasks_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY tasks
    ADD CONSTRAINT tasks_pkey PRIMARY KEY (id);


--
-- Name: tasks_relationships_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY tasks_relationships
    ADD CONSTRAINT tasks_relationships_pkey PRIMARY KEY (id);


--
-- Name: type_task_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY type_task
    ADD CONSTRAINT type_task_pkey PRIMARY KEY (id);


--
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: epicodus
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM epicodus;
GRANT ALL ON SCHEMA public TO epicodus;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

