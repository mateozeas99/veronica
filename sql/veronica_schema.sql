--
-- PostgreSQL database dump
--

-- Dumped from database version 11.1
-- Dumped by pg_dump version 11.1

-- Started on 2019-02-21 20:59:24

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2 (class 3079 OID 16394)
-- Name: pgcrypto; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;


--
-- TOC entry 2895 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION pgcrypto; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION pgcrypto IS 'cryptographic functions';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 197 (class 1259 OID 16431)
-- Name: digital_cert; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.digital_cert (
    digital_cert_id integer NOT NULL,
    digital_cert bytea NOT NULL,
    owner character varying(20),
    password text,
    active boolean,
    insert_date date
);


ALTER TABLE public.digital_cert OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 16437)
-- Name: digital_cert_digital_cert_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.digital_cert_digital_cert_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.digital_cert_digital_cert_id_seq OWNER TO postgres;

--
-- TOC entry 2896 (class 0 OID 0)
-- Dependencies: 198
-- Name: digital_cert_digital_cert_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.digital_cert_digital_cert_id_seq OWNED BY public.digital_cert.digital_cert_id;


--
-- TOC entry 199 (class 1259 OID 16439)
-- Name: digital_cert_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.digital_cert_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.digital_cert_seq OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 16441)
-- Name: internal_status; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.internal_status (
    internal_status_id integer NOT NULL,
    description character varying(30) NOT NULL
);


ALTER TABLE public.internal_status OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 16444)
-- Name: internal_status_internal_status_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.internal_status_internal_status_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.internal_status_internal_status_id_seq OWNER TO postgres;

--
-- TOC entry 2897 (class 0 OID 0)
-- Dependencies: 201
-- Name: internal_status_internal_status_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.internal_status_internal_status_id_seq OWNED BY public.internal_status.internal_status_id;


--
-- TOC entry 202 (class 1259 OID 16446)
-- Name: invoice; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.invoice (
    invoice_id integer NOT NULL,
    access_key character varying(50) NOT NULL,
    sri_version character varying(5) NOT NULL,
    xml_content xml,
    supplier_id character varying(20) NOT NULL,
    customer_id character varying(20) NOT NULL,
    issue_date date,
    internal_status_id integer,
    invoice_number character varying(20),
    xml_authorization xml,
    is_deleted boolean DEFAULT false,
    authorization_date timestamp without time zone
);


ALTER TABLE public.invoice OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 16453)
-- Name: invoice_invoice_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.invoice_invoice_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.invoice_invoice_id_seq OWNER TO postgres;

--
-- TOC entry 2898 (class 0 OID 0)
-- Dependencies: 203
-- Name: invoice_invoice_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.invoice_invoice_id_seq OWNED BY public.invoice.invoice_id;


--
-- TOC entry 204 (class 1259 OID 16455)
-- Name: invoice_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.invoice_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.invoice_seq OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 16487)
-- Name: with_holding_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.with_holding_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.with_holding_seq OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 16504)
-- Name: with_holding; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.with_holding (
    with_holding_id integer DEFAULT nextval('public.with_holding_seq'::regclass) NOT NULL,
    access_key character varying(50) NOT NULL,
    sri_version character varying(5) NOT NULL,
    xml_content xml,
    supplier_id character varying(20) NOT NULL,
    customer_id character varying(20) NOT NULL,
    issue_date date,
    internal_status_id integer,
    xml_authorization xml,
    is_deleted boolean DEFAULT false,
    authorization_date timestamp without time zone
);


ALTER TABLE public.with_holding OWNER TO postgres;

--
-- TOC entry 2747 (class 2604 OID 16457)
-- Name: digital_cert digital_cert_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.digital_cert ALTER COLUMN digital_cert_id SET DEFAULT nextval('public.digital_cert_digital_cert_id_seq'::regclass);


--
-- TOC entry 2748 (class 2604 OID 16458)
-- Name: internal_status internal_status_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.internal_status ALTER COLUMN internal_status_id SET DEFAULT nextval('public.internal_status_internal_status_id_seq'::regclass);


--
-- TOC entry 2750 (class 2604 OID 16459)
-- Name: invoice invoice_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.invoice ALTER COLUMN invoice_id SET DEFAULT nextval('public.invoice_invoice_id_seq'::regclass);


--
-- TOC entry 2754 (class 2606 OID 16461)
-- Name: digital_cert digital_cert_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.digital_cert
    ADD CONSTRAINT digital_cert_pkey PRIMARY KEY (digital_cert_id);


--
-- TOC entry 2756 (class 2606 OID 16463)
-- Name: internal_status internal_status_description_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.internal_status
    ADD CONSTRAINT internal_status_description_key UNIQUE (description);


--
-- TOC entry 2758 (class 2606 OID 16465)
-- Name: internal_status internal_status_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.internal_status
    ADD CONSTRAINT internal_status_pkey PRIMARY KEY (internal_status_id);


--
-- TOC entry 2760 (class 2606 OID 16467)
-- Name: invoice invoice_access_key_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.invoice
    ADD CONSTRAINT invoice_access_key_key UNIQUE (access_key);


--
-- TOC entry 2762 (class 2606 OID 16469)
-- Name: invoice invoice_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.invoice
    ADD CONSTRAINT invoice_pkey PRIMARY KEY (invoice_id);


--
-- TOC entry 2764 (class 2606 OID 16515)
-- Name: with_holding with_holding_access_key_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.with_holding
    ADD CONSTRAINT with_holding_access_key_key UNIQUE (access_key);


--
-- TOC entry 2766 (class 2606 OID 16513)
-- Name: with_holding with_holding_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.with_holding
    ADD CONSTRAINT with_holding_pkey PRIMARY KEY (with_holding_id);


--
-- TOC entry 2767 (class 2606 OID 16470)
-- Name: invoice invoice_internal_status_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.invoice
    ADD CONSTRAINT invoice_internal_status_id_fkey FOREIGN KEY (internal_status_id) REFERENCES public.internal_status(internal_status_id);


--
-- TOC entry 2768 (class 2606 OID 16516)
-- Name: with_holding with_holding_internal_status_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.with_holding
    ADD CONSTRAINT with_holding_internal_status_id_fkey FOREIGN KEY (internal_status_id) REFERENCES public.internal_status(internal_status_id);


-- Completed on 2019-02-21 20:59:26

--
-- PostgreSQL database dump complete
--