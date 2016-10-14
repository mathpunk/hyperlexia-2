(ns anansi.reading.app
  (:require [reagent.core :as reagent :refer [atom]]
            [anansi.reading.db :as db]))

(def state (atom db/data))

(defn summary-component [data]
  (do (. js/console log "input: " (js/JSON.stringify db/input))
   (. js/console log "data: " @state)
    [:div#summary
      [:div#today "today's date is 2016-10-13"]
      [:div#recent "these posts are from " (:date @state)]
      [:div#data
        [:div#keys "They have these keys: "
          (clojure.string/join  " " (:keys @state))]
        [:div#user "They have this user: " (:user @state)]
        [:div#posts "There are fewer than " (+ 1 (count (:posts @state))) " posts"]
      ]]))

(defn welcoming-component []
  [:div#welcome "Good morning--"
   [summary-component]])

(defn app-component []
  [welcoming-component])

(defn init []
  (reagent/render-component [app-component]
                            (.getElementById js/document "container")))
