
(ns cljs-dnd.wrapper)

;; TODO
(defn drag-wrapper)

;; TODO
(defn drop-wrapper)

(defn wrapper []
  (.createClass
    reagent/react
    #js {:componentWillMount (fn [])
         :componentDidMount (fn [])
         :componentWillUpdate (fn [next-props]
                                (this-as this
                                  (when (and next-props
                                             (.-props this)))))
         :render (fn []
                   (this-as this
                     (let [app-db (.-wrap (.-props this))
                           children (.-children (.-props this))]
                       (reagent/as-element
                            [:div.board-content
                              children]))))}))

; (defn draggable []
;   (reagent/create-class {:reagent-render draggable-render
;                          :component-did-mount         draggable-did-mount}))

; (defn greeter
;   [name]
;   [:div "Hello: " name])
;
; (defn decorate
;   [& HoCs]
;   (into [:div] HoCs))
;
; ;; markup:
; [decorate [greeter "Spot"] [greeter "Fiddo"]]

; :draggable true ; -> otherwise the browser won't let you drag it
; :on-drag-over allow-drop
; :on-drag-enter allow-drop
; :on-drag-start #(.setData (.-dataTransfer %) "text/plain" "") ;; for Firefox. You MUST set something as data.
; :on-drag-end some-action
; :on-drop some-other-action

; (.setData (.-dataTransfer e) "text/plain" "") ;; for Firefox.
