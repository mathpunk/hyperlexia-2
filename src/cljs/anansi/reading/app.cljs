(ns anansi.reading.app
  (:require [reagent.core :as reagent :refer [atom]]
            [anansi.reading.db :as db]))

(def state
  (let [data db/data
        view {:posts {:active (nth (:posts data) 0)}}]
    (atom (merge data view))))

(defn card [{:keys []}]
  "Our first real component."
  []
  )

(defn summary [data]
  (do (. js/console log "input: " (js/JSON.stringify db/input))
   (. js/console log "data: " @state)
    [:div#summary
      [:div#today "today's date is 2016-10-13"]
      [:div#recent "these posts are from " (:date @state)]
      [:div#data
        [:div#keys "They have these keys: "
          (clojure.string/join  " " (:keys @state))]
        [:div#user "They have this user: " (:user @state)]
        [:div#posts "There are fewer than " (+ 1 (count (:posts @state))) " posts"
        [:div#active-post "This is the first post, unrendered: " (first (:posts @state))]
        [:div.post [:p "The keys of a post are"  (get-in @state [:view :active])]]
        ]
      ]]))

(defn welcome []
  [:div#welcome "Good morning--"])

(defn app []
  [:div#app
    [welcome]
    [summary]
  ])

(defn init []
  (reagent/render-component [app]
                            (.getElementById js/document "container")))
