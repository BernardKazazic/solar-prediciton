import json
import pickle
import numpy as np
from kafka import KafkaConsumer
from sqlalchemy import create_engine
from sqlalchemy import insert
import pandas as pd

consumer = KafkaConsumer(
    'regression',
    bootstrap_servers='localhost:9092',
    max_poll_records = 100,
    value_deserializer=lambda m: json.loads(m.decode('ascii')),
    auto_offset_reset='earliest',
)

# Spremanje predvidanja u bazu
def save_to_database(prediction):
    engine = create_engine('postgresql://username:password@localhost:5432/baza')
    with engine.connect() as conn:
        stmt = insert(power_forecast).values(tof=prediction['tof'], vt=prediction['power_timestamp'], model_id=prediction['model_id'], qty_vt=prediction['qyt'])
        conn.execute(stmt)

# stvaranje predvidanja
def predict(dataframe_production, dataframe_weather, model, model_id):
    current_datetime = dataframe_weather['vt'].min()
    end_datetime = dataframe_weather['vt'].max()

    while current_datetime <= end_datetime:
        
        weather_current_row = dataframe_weather.loc[dataframe_weather['vt'] == current_datetime].reset_index(drop=True)
        production_current_row = dataframe_production.loc[(dataframe_production['power_timestamp'] == current_datetime)].reset_index(drop=True)
        production_15min_row = dataframe_production.loc[(dataframe_production['power_timestamp'] == current_datetime - pd.Timedelta(minutes=15))].reset_index(drop=True)
        production_30min_row = dataframe_production.loc[(dataframe_production['power_timestamp'] == current_datetime - pd.Timedelta(minutes=30))].reset_index(drop=True)
        production_45min_row = dataframe_production.loc[(dataframe_production['power_timestamp'] == current_datetime - pd.Timedelta(minutes=45))].reset_index(drop=True)
        production_60min_row = dataframe_production.loc[(dataframe_production['power_timestamp'] == current_datetime - pd.Timedelta(minutes=60))].reset_index(drop=True)

        prediction_row = [weather_current_row.iloc[0, 2], weather_current_row.iloc[0, 3], weather_current_row.iloc[0, 4], weather_current_row.iloc[0, 5], weather_current_row.iloc[0, 6], weather_current_row.iloc[0, 7], weather_current_row.iloc[0, 8], production_current_row.iloc[0, 1], production_15min_row.iloc[0, 1], production_30min_row.iloc[0, 1], production_45min_row.iloc[0, 1], production_60min_row.iloc[0, 1]]
        
        predicted_num = model.predict(np.array(prediction_row).reshape(1, -1))[0]
        
        current_datetime += pd.Timedelta(minutes=15)
        
        new_row = pd.DataFrame({'power_timestamp': [current_datetime], ' qyt': [predicted_num], 'tof': [weather_current_row.iloc[0, 1]], 'model_id': [model_id]})
        dataframe_production = pd.concat([dataframe_production,new_row], ignore_index=True)
 
    return dataframe_production.sort_values(by='power_timestamp')



for msg in consumer:
    data = json.loads(msg.value)

    with open(data.model, 'rb') as f:
        model = pickle.load(f)

    prediction = predict(data.production, data.weather, model, data.model_id)
    
    save_to_database(prediction)