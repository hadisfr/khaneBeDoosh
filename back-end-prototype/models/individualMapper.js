const dbconfig = require('./dbconfig.json');
const sqlite3 = require('sqlite3').verbose();
var debug = require('debug')('khanebedoosh:domain');

class individualMapper {
    getByUsername(username) {
        let db = new sqlite3.Database(
            dbconfig.path,
            sqlite3.OPEN_READWRITE,
            err => {
                if (err) {
                    return debug(err.message);
                }
                debug('Connected to the SQlite database.');
            }
        );

        let query =
            'select * from ' +
            dbconfig.IndividualTableName +
            ' where ' +
            dbconfig.UsernameKey +
            ' =?';

        db.each(query, [username], (err, row) => {
            if (err) {
                throw err;
            }
        });

        db.close(err => {
            if (err) {
                return debug(err.message);
            }
            debug('Close the database connection.');
        });
    }
}

module.exports = individualMapper;
