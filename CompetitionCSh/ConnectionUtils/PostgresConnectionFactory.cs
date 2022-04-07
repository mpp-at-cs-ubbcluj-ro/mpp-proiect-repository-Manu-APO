using System.Configuration;
using System.Data;
using Npgsql;

namespace ConnectionUtils
{
    public class PostgresConnectionFactory : ConnectionFactory
    {
        public override IDbConnection createConnection()
        {
            return new NpgsqlConnection(ConfigurationManager.ConnectionStrings["competition"].ConnectionString);
        }
    }
}