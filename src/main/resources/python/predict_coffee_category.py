import pickle
import sys
from datetime import datetime

LABEL_TO_CATEGORY = {
    "블랙계": "BLACK",
    "크리미계": "CREAMY",
    "달콤계": "SWEET",
}


def discomfort_index(temperature, humidity):
    return 0.81 * temperature + 0.01 * humidity * (0.99 * temperature - 14.3) + 46.3


def heat_index_celsius(temperature, humidity):
    temp_f = (temperature * 9.0 / 5.0) + 32.0
    hi_f = (
        -42.379
        + 2.04901523 * temp_f
        + 10.14333127 * humidity
        - 0.22475541 * temp_f * humidity
        - 0.00683783 * temp_f * temp_f
        - 0.05481717 * humidity * humidity
        + 0.00122874 * temp_f * temp_f * humidity
        + 0.00085282 * temp_f * humidity * humidity
        - 0.00000199 * temp_f * temp_f * humidity * humidity
    )
    return (hi_f - 32.0) * 5.0 / 9.0


def season(month):
    if month in (12, 1, 2):
        return 0
    if month in (3, 4, 5):
        return 1
    if month in (6, 7, 8):
        return 2
    return 3


def build_features(temperature, humidity, temperature_diff_1h, recorded_at):
    hour = recorded_at.hour
    month = recorded_at.month
    dayofweek = recorded_at.weekday()

    return [
        temperature,
        humidity,
        discomfort_index(temperature, humidity),
        heat_index_celsius(temperature, humidity),
        temperature_diff_1h,
        hour,
        month,
        dayofweek,
        season(month),
        1 if dayofweek >= 5 else 0,
        1 if 5 <= hour < 11 else 0,
        1 if 11 <= hour < 14 else 0,
        1 if 14 <= hour < 18 else 0,
        1 if 18 <= hour < 24 else 0,
    ]


def main():
    if len(sys.argv) != 6:
        raise ValueError("Usage: predict_coffee_category.py <model_path> <temperature> <humidity> <temperature_diff_1h> <recorded_at>")

    model_path = sys.argv[1]
    temperature = float(sys.argv[2])
    humidity = float(sys.argv[3])
    temperature_diff_1h = float(sys.argv[4])
    recorded_at = datetime.fromisoformat(sys.argv[5])

    with open(model_path, "rb") as model_file:
        bundle = pickle.load(model_file)

    model = bundle["model"]
    label_encoder = bundle.get("label_encoder")
    class_names = bundle.get("class_names")

    features = build_features(temperature, humidity, temperature_diff_1h, recorded_at)
    prediction = model.predict([features])[0]

    if label_encoder is not None:
        label = label_encoder.inverse_transform([prediction])[0]
    elif class_names is not None:
        label = class_names[int(prediction)]
    else:
        label = prediction

    print(LABEL_TO_CATEGORY.get(str(label), str(label)).strip())


if __name__ == "__main__":
    main()
