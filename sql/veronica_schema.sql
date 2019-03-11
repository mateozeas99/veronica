--
-- PostgreSQL database dump
--

-- Dumped from database version 11.1
-- Dumped by pg_dump version 11.1

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
-- Name: pgcrypto; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;


--
-- Name: EXTENSION pgcrypto; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION pgcrypto IS 'cryptographic functions';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: bol; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bol (
    bol_id integer NOT NULL,
    access_key character varying(50) NOT NULL,
    internal_status_id integer,
    sri_version character varying(5) NOT NULL,
    xml_content xml,
    supplier_id character varying(20) NOT NULL,
    issue_date date,
    bol_number character varying(20),
    xml_authorization xml,
    is_deleted boolean DEFAULT false,
    authorization_date timestamp without time zone,
    shipper_ruc character varying(20) NOT NULL,
    registration_number character varying(20)
);


ALTER TABLE public.bol OWNER TO postgres;

--
-- Name: bol_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.bol_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.bol_seq OWNER TO postgres;

--
-- Name: consignne; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.consignne (
    consignne_id integer NOT NULL,
    access_key character varying(50) NOT NULL,
    consignne_number character varying(20) NOT NULL,
    custom_doc_number character varying(20) NOT NULL,
    reference_doc_cod character varying(5) NOT NULL,
    reference_doc_number character varying(20) NOT NULL,
    reference_doc_auth_number character varying(50) NOT NULL
);


ALTER TABLE public.consignne OWNER TO postgres;

--
-- Name: consignne_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.consignne_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.consignne_seq OWNER TO postgres;

--
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
-- Name: digital_cert_digital_cert_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.digital_cert_digital_cert_id_seq OWNED BY public.digital_cert.digital_cert_id;


--
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
-- Name: internal_status; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.internal_status (
    internal_status_id integer NOT NULL,
    description character varying(30) NOT NULL
);


ALTER TABLE public.internal_status OWNER TO postgres;

--
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
-- Name: internal_status_internal_status_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.internal_status_internal_status_id_seq OWNED BY public.internal_status.internal_status_id;


--
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
-- Name: invoice_invoice_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.invoice_invoice_id_seq OWNED BY public.invoice.invoice_id;


--
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
-- Name: digital_cert digital_cert_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.digital_cert ALTER COLUMN digital_cert_id SET DEFAULT nextval('public.digital_cert_digital_cert_id_seq'::regclass);


--
-- Name: internal_status internal_status_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.internal_status ALTER COLUMN internal_status_id SET DEFAULT nextval('public.internal_status_internal_status_id_seq'::regclass);


--
-- Name: invoice invoice_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.invoice ALTER COLUMN invoice_id SET DEFAULT nextval('public.invoice_invoice_id_seq'::regclass);


--
-- Name: bol bol_access_key_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bol
    ADD CONSTRAINT bol_access_key_key UNIQUE (access_key);


--
-- Name: bol bol_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bol
    ADD CONSTRAINT bol_pkey PRIMARY KEY (bol_id);


--
-- Name: consignne consignne_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consignne
    ADD CONSTRAINT consignne_pkey PRIMARY KEY (consignne_id);


--
-- Name: digital_cert digital_cert_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.digital_cert
    ADD CONSTRAINT digital_cert_pkey PRIMARY KEY (digital_cert_id);


--
-- Name: internal_status internal_status_description_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.internal_status
    ADD CONSTRAINT internal_status_description_key UNIQUE (description);


--
-- Name: internal_status internal_status_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.internal_status
    ADD CONSTRAINT internal_status_pkey PRIMARY KEY (internal_status_id);


--
-- Name: invoice invoice_access_key_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.invoice
    ADD CONSTRAINT invoice_access_key_key UNIQUE (access_key);


--
-- Name: invoice invoice_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.invoice
    ADD CONSTRAINT invoice_pkey PRIMARY KEY (invoice_id);


--
-- Name: with_holding with_holding_access_key_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.with_holding
    ADD CONSTRAINT with_holding_access_key_key UNIQUE (access_key);


--
-- Name: with_holding with_holding_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.with_holding
    ADD CONSTRAINT with_holding_pkey PRIMARY KEY (with_holding_id);


--
-- Name: consignne bol_consignne_access_key_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consignne
    ADD CONSTRAINT bol_consignne_access_key_fkey FOREIGN KEY (access_key) REFERENCES public.bol(access_key);


--
-- Name: bol bol_internal_status_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bol
    ADD CONSTRAINT bol_internal_status_id_fkey FOREIGN KEY (internal_status_id) REFERENCES public.internal_status(internal_status_id);


--
-- Name: invoice invoice_internal_status_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.invoice
    ADD CONSTRAINT invoice_internal_status_id_fkey FOREIGN KEY (internal_status_id) REFERENCES public.internal_status(internal_status_id);


--
-- Name: with_holding with_holding_internal_status_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.with_holding
    ADD CONSTRAINT with_holding_internal_status_id_fkey FOREIGN KEY (internal_status_id) REFERENCES public.internal_status(internal_status_id);


--
-- PostgreSQL database dump complete
--

