using CompetitionCSh.domain.entities;
using CompetitionCSh.repository.interfaces;
using CompetitionCSh.utils;
using log4net;
using Npgsql;

namespace CompetitionCSh.repository;

public class RegistryDbRepository : IRegistryRepository
{
    private static readonly ILog log = LogManager.GetLogger("RegistryDbRepository");

    public RegistryDbRepository()
    {
        log.Info("Creating RegistryDbRepository");
    }

    public IEnumerable<Registry> findAll()
    {
        throw new NotImplementedException();
    }

    public void save(Registry entity)
    {
        throw new NotImplementedException();
    }

    public void update(Registry e1, Registry e2)
    {
        throw new NotImplementedException();
    }

    public Registry delete(Registry e)
    {
        throw new NotImplementedException();
    }

    public Registry findOne(string id)
    {
        log.InfoFormat("Entering findOne with value= {0}", id);
        var con = DBUtils.getConnection();
        using (var comm = con.CreateCommand())
        {
            comm.CommandText = "select * from \"Registry\" where id=@id";
            comm.Parameters.Add(new NpgsqlParameter("@id", id));
            using (var dataR = comm.ExecuteReader())
            {
                if (dataR.Read())
                {
                    var userName = dataR.GetString(1);
                    var password = dataR.GetString(2);
                    var firstName = dataR.GetString(3);
                    var lastName = dataR.GetString(4);
                    var isAdmin = dataR.GetBoolean(5);
                    var registry = new Registry(userName, password, firstName, lastName, isAdmin);
                    registry.Id = id;
                    log.InfoFormat("Exiting findOne with value {0}", registry);
                    return registry;
                }
            }
        }

        log.InfoFormat("Exiting findOne with value {0}", null);
        return null;
    }

    public Registry findRegistryByCredetials(string userName, string password)
    {
        log.InfoFormat("Entering findRegistryByCredetials with username= {0}, password= {1}", userName, password);
        var con = DBUtils.getConnection();
        using (var comm = con.CreateCommand())
        {
            comm.CommandText = "select * from \"Registry\" where userName=@userName and password=@password";
            comm.Parameters.Add(new NpgsqlParameter("@userName", userName));
            comm.Parameters.Add(new NpgsqlParameter("@password", password));
            using (var dataR = comm.ExecuteReader())
            {
                if (dataR.Read())
                {
                    var id = dataR.GetString(0);
                    var firstName = dataR.GetString(3);
                    var lastName = dataR.GetString(4);
                    var isAdmin = dataR.GetBoolean(5);
                    var registry = new Registry(userName, password, firstName, lastName, isAdmin);
                    registry.Id = id;
                    log.InfoFormat("Exiting findRegistryByCredetials with value {0}", registry);
                    return registry;
                }
            }
        }

        log.InfoFormat("Exiting findRegistryByCredetials with value {0}", null);
        return null;
    }
}