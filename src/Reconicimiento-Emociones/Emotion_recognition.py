import cv2
import numpy as np
import tensorflow as tf
from tensorflow.keras.models import load_model


emotions = ["aburrido", "enganchado", "excitado", "concentrado",
            "interesado", "relajado"]

model = load_model("Emotion_recognition.h5")

face_cascade = cv2.CascadeClassifier("haarcascade_frontalface_alt.xml")

capture = cv2.VideoCapture(1)
while(True):
    ret, frame = capture.read()
    imagenMostrada = frame.copy()
    gray = cv2.cvtColor(frame,cv2.COLOR_BGR2GRAY)
    resizeFace = cv2.resize(gray, (150, 150))
    detected_face = face_cascade.detectMultiScale(gray, 1.1, 5, 30)
    emotion = "No detectada"
    if len(detected_face) > 0:
        for (x, y, w, h) in detected_face:
            cv2.rectangle(frame, (x, y), (x + w, y + h), (255, 0, 0), 0)
            sub_face = frame[y:y + h, x:x + w]
            resizeFace = cv2.resize(sub_face, (150, 150), interpolation=cv2.INTER_LINEAR)
        resizeFace = cv2.cvtColor(resizeFace, cv2.COLOR_BGR2GRAY)
        tfFace = tf.cast(resizeFace, tf.float32)
        prediccion = model.predict(np.reshape(resizeFace, (1, 150, 150)))
        indice = np.argmax(prediccion[0])
        emotion = emotions[indice]
        f = open('result.txt', 'w')
        f.write(emotion)
        f.truncate()
        f.close()
    imagenMostrada = cv2.putText(imagenMostrada, emotion, (5, 20), cv2.FONT_HERSHEY_SIMPLEX,1, color=(255, 0, 0), thickness=1)
    cv2.imshow('Rostro - Presione [q] para salir', imagenMostrada)
    if cv2.waitKey(10) & 0xFF == ord('q'):
       break
capture.release()
cv2.destroyAllWindows()

