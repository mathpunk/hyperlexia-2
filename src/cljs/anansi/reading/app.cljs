(ns anansi.reading.app
  (:require [reagent.core :as reagent :refer [atom]]
            [ajax.core :refer [GET POST]]
            [anansi.reading.components :as c]
            [clojure.walk :refer [keywordize-keys]]
            [clojure.tools.reader.edn :as edn]
            [datascript.core :as d]
            [reagent-material-ui.core :refer [List ListItem]] ))
(enable-console-print!)

(defonce state (atom {}))

(declare add-pin)

(defn handler [res]
    (let [edn (edn/read-string res)]
      (. js/console log "data does arrive from the server; here's an item with keys " (clj->js (keys (first (:posts edn)))))
      (reset! state edn)              ;; the state atom gets filled
      (map add-pin (:posts edn))))    ;; but the db does not trigger log messages,
                                      ;; and I kinda doubt is has anything in it? 

(defn error-handler [{:keys [status status-text]}]
  (.log js/console (str "something bad happened: " status " " status-text)))

(GET "/recent" {:handler handler
              :error-handler error-handler})

(def schema
  ;; { :entity/attribute {:db/attribute :db.attribute/value} ... }
    {:pin/user {:db/valueType :db.type/ref}
     :pin/href {:db/cardinatlity :db.cardinality/one}
     :pin/tags {:db/cardinality :db.cardinality/many}
    })

(defonce conn (do (. js/console log "Creating connection") (d/create-conn schema)))

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

;; Views
;; =====
(defn welcome-pane []
  [:div#welcome [:h2 "Good morning--"]])

(defn summary-pane [db]
    [:div#summary
      [:div#today "today is 2016-10-13"]
      [:div#source
        (str "viewing " (:user @db) "'s pins, retrieved " (:date @db))]
      [:div#progress (str (count (:posts @db)) " pins to review")]
    ])

(defn review-pane [db]
  ;; (d/transact! conn [{:pins/description "hiii"}])
  [:div#pins {:style {:margin-top 20}} [:h3 "Data"] (:eavt @db)])

(defn app [db]
  [:div#app
    [welcome-pane]
    [summary-pane state]
    [review-pane state]
  ])

(defn render
  ([] (render @conn))
  ([db] (reagent/render-component [app db]
                            (.getElementById js/document "container"))))

(d/listen! conn :render
  (fn [tx-report] (render (:db-after tx-report))))

(d/listen! conn :log
  (fn [tx-report] (println (:tx-data tx-report))))
