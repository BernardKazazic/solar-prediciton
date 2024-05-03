CREATE TABLE IF NOT EXISTS public.weather_forecast
(
    tof timestamp without time zone NOT NULL,
    vt timestamp without time zone NOT NULL,
    barometer double precision,
    outtemp double precision,
    windspeed double precision,
    winddir integer,
    rain double precision,
    radiation integer,
    cloud_cover double precision,
    CONSTRAINT weather_forecast_pkey PRIMARY KEY (tof, vt)
);

CREATE TABLE IF NOT EXISTS public.historical_production
(
    production_timestamp timestamp without time zone NOT NULL,
    qyt double precision,
    CONSTRAINT historical_production_pkey PRIMARY KEY (production_timestamp)
);

CREATE TABLE IF NOT EXISTS public.power_forecast
(
    tof timestamp without time zone NOT NULL,
    vt timestamp without time zone NOT NULL,
    qty_vt double precision,
    CONSTRAINT forecast_pkey PRIMARY KEY (tof, vt)
);

