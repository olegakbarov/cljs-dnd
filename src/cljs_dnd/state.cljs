
(ns cljs-dnd.state)

(def state (atom {:dix nil       ;; drag-index
                  :hix nil       ;; hover-index
                  :cli-y nil}))  ;; clientY

(defn update-state [key val]
  (let [keyz (set (keys @state))]
    (if (contains? keyz key)
      (swap! state assoc key val)
      ("ERROR"))))
