-- Table: public.Records

-- DROP TABLE public."Records";

CREATE TABLE public."Records"
(
    id bigserial PRIMARY KEY,
    x integer NOT NULL,
    y integer NOT NULL,
    velocity double precision NOT NULL,
    heading double precision NOT NULL,
	created_time timestamp default current_timestamp,
    session_id bigint NOT NULL,
    CONSTRAINT valid_session_id_constraint FOREIGN KEY (session_id)
            REFERENCES public."Sessions" (id) MATCH SIMPLE
            ON UPDATE NO ACTION
            ON DELETE CASCADE
            NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE public."Records"
    OWNER to admin;

-- Table: public.Records

-- DROP TABLE public."Sessions";

CREATE TABLE public."Sessions"
(
    id bigserial PRIMARY KEY,
	start_time timestamp default current_timestamp,
	end_time timestamp,
    title varchar(100)
)

TABLESPACE pg_default;

ALTER TABLE public."Sessions"
    OWNER to admin;