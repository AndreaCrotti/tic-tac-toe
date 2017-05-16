# tic-tac-toe

[![Build Status](https://travis-ci.org/AndreaCrotti/tic-tac-toe.svg?branch=master)](https://travis-ci.org/AndreaCrotti/tic-tac-toe)

## Usage

    lein run -c CONFIG_FILE -p PROFILE

Where the default configuration is an
(EDN file)[resources/config.edn], and each profile configures
the initial player, the size of the board and the algorithm
which each player should use.

## TODO

- [x] implement more strategies
    - [x] random algorithm
    - [x] blocker algorithm, simply try to block the opponent
    - [x] best move algorithm

- [ ] store game results with an in memory datascript database
- [ ] generate some interesting reports about the winner, using different strategies
- [ ] make the various algorithm compete against each other and see who the winner is
- [ ] implement a UI with re-frame to show the whole thing up and running
- [ ] add CLI interface reading from stdin

## License

Copyright © 2017 Andrea Crotti

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
