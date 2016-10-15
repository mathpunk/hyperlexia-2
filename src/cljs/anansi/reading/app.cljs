(ns anansi.reading.app
  (:require [reagent.core :as reagent :refer [atom]]
            [anansi.reading.db :as db]
            [anansi.reading.components :as c]
            [datascript.core :as d]
            [reagent-material-ui.core :refer [List ListItem]] ))

(enable-console-print!)

(def schema {})
(defonce conn (d/create-conn schema))

;; Data
;; ====
;; 1. Comes in as a file requirement
;; 2. Transform and put into a datascript in-memory database
;; 3. Query against the db -- posh?


;; Reactive Atom
;; -------------
(defonce state
  (let [data db/data
        session {:unread (count (:pins db/data))}]
        (atom (merge db/data session))))

;; Data
;; ----

;; Views
;; =====
(defn welcome []
  [:div#welcome [:h2 "Good morning--"]])

(defn summary []
    [:div#summary
      [:div#today "today is 2016-10-13"]
      [:div#source
        (str "viewing " (:user @state) "'s pins, retrieved " (:date @state))]
      [:div#progress (str (count (:pins @state)) " pins to review")]
    ])

(defn pins []
  [:div#pins {:style {:margin-top 20}}
    (map c/card (:pins @state))
  ])

(defn app [db]
  [:div#app
    [welcome]
    [summary]
    [pins]
  ])

(defn render
  ([] (render @conn))
  ([db] (reagent/render-component [app db]
                            (.getElementById js/document "container"))))
(d/listen! conn :render
  (fn [tx-report] (render (:db-after tx-report))))
