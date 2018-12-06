(let [input (slurp "day5input.txt")
      reactions (ref 0)
      excluded_indices (ref #{})
      abs (fn [n] (if (< n 0) (* n -1) n))
      react? (fn [left right] (= (abs (- (int left) (int right))) 32))
      current (ref 0)
      next (ref 1)
      strsize (dec (count input))]

  (loop []
    (let [a (nth input @current) b (nth input @next)]
      (if (react? a b)
        (let [still_reacts (atom true)]
          (dosync
            (alter excluded_indices conj @current @next)
            (alter reactions inc))

          (while (and (> @current 0) (< @next (dec strsize)) @still_reacts)
            (dosync (alter current dec) (alter next inc))
            (if (react? (nth input @current) (nth input @next))
              (dosync
                (alter excluded_indices conj @current @next)
                (alter reactions inc))
              (reset! still_reacts false)))

          (dosync
            (ref-set current @next)
            (alter next inc)))

        (dosync
          (alter current inc) (alter next inc))))

    (if (< @next strsize) (recur)))

  (println (- strsize (* 2 @reactions))))
