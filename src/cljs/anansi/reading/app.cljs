(ns anansi.reading.app
  (:require [reagent.core :as reagent :refer [atom]]
            [ajax.core :refer [GET POST]]
            [anansi.reading.components :as c]
            [clojure.walk :refer [keywordize-keys]]
            [cognitect.transit :as transit]
          [clojure.tools.reader.edn :as edn]
            [datascript.core :as d]
            [reagent-material-ui.core :refer [List ListItem]] ))
(enable-console-print!)

; (defn recent-pins [cb]
;   (let [data (GET "/data")]
;     (cb data)))

; (defn read [s]
;   (let [r (transit/reader :json)]
;     (transit/read r s)
;   ))

(defn handler [response]
  (let [data (edn/read-string response)]
  (.log js/console (clojure.string/join " " (keys data)))) )

(defn error-handler [{:keys [status status-text]}]
  (.log js/console (str "something bad happened: " status " " status-text)))

(GET "/data" {:handler handler
               :error-handler error-handler})

(def schema
  ;; { :entity/attribute {:db/attribute :db.attribute/value} ... }
    {:pin/user {:db/valueType :db.type/ref}
     :pin/href {:db/cardinatlity :db.cardinality/one}
     :pin/tags {:db/cardinality :db.cardinality/many}
    })

(defonce conn (do (. js/console log "Creating connection") (d/create-conn schema)))

(defn destructure-tweet [tw]
  (if-let [matches (re-matches #"https://twitter\.com/(\w+)/status/(]0-9]+).*" (:href tw))]
    (assoc tw :user (nth matches 1) :status-id (nth matches 2))
    tw
  ))

(defn add-pin [pin]
  (let [ent {:pin/time (:time pin)
             :pin/href (:href pin)
             :pin/description (:description pin)
             :pin/extended (:extended pin)
             :pin/meta (:meta pin)
             :pin/hash (:hash pin)
             :pin/shared (:shared pin)
             :pin/toread (:toread pin)
             ;; :pin/user (:user pin)
             ;; :pin/status-id (:status-id pin)
             ;; :pin/tags (clojure.string/split (:tags pin) #" ") }]
           }]
        (do (println "adding pin")
          (d/transact! conn [ent]))
      ))

; (defonce load
;   (do
;     (. js/console log "Loading: " (clj->js (:posts data)))
;     (. js/console log "First post: " (clj->js (first (:posts data))))
;     (map add-pin (:posts data))))


;; Views
;; =====
(defn welcome-pane []
  [:div#welcome [:h2 "Good morning--"]])

(defn summary-pane [db]
    [:div#summary
      [:div#today "today is 2016-10-13"]
      [:div#source
        (str "viewing " (:user db) "'s pins, retrieved " (:date db))]
      [:div#progress (str (count (:pins db)) " pins to review")]
    ])

(defn review-pane [db]
  ;; (d/transact! conn [{:pins/description "hiii"}])
  [:div#pins {:style {:margin-top 20}} [:h3 "Data"]
  [:p data]])

(defn app [db]
  [:div#app
    [welcome-pane]
    [summary-pane data]
    [review-pane data]
  ])

(defn render
  ([] (render @conn))
  ([db] (reagent/render-component [app db]
                            (.getElementById js/document "container"))))

(d/listen! conn :render
  (fn [tx-report] (render (:db-after tx-report))))

(d/listen! conn :log
  (fn [tx-report] (println (:tx-data tx-report))))
