
(ns cljs-dnd.wrapper
  (:require [reagent.core :as r]
            [cljs-dnd.core :refer [dispatch]]
            [cljs-dnd.state :refer [state]]))

(defn drag-wrapper [opts component]
  (let [{:keys [drag-type]} opts
        decorated-props (merge opts @state)]
    (r/create-class
       {:component-did-mount
         (fn [this]
           (let [node (reagent.dom/dom-node this)]
             (do
               (dispatch [:register-source node]))))
        :render
          (fn []
            ;  (.log js/console (clj->js decorated-props))
             (this-as this
              (r/create-element
                ;; TODO
                "ReactClass"
                ;; we drop-in all the dnd-state into the comp
                (clj->js decorated-props)
                (r/as-element component))))})))

;; -------------------------------
;; r/create-class signature

; {:get-initial-state
;    (fn [this])
;  :component-will-receive-props
;    (fn [this new-argv])
;  :should-component-update
;    (fn [this old-argv new-argv])
;  :component-will-mount
;    (fn [this])
;  :component-did-mount
;    (fn [this])
;  :component-will-update
;    (fn [this new-argv])
;  :component-did-update
;    (fn [this old-argv])
;  :component-will-unmount
;    (fn [this])
;  :render
;    (fn []
;      (this-as this))}
