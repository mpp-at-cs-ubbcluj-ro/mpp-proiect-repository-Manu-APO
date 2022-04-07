using CompetitionCSh.domain.entities;
using CompetitionCSh.repository.interfaces;
using CompetitionCSh.utils;
using log4net;
using Npgsql;

namespace CompetitionCSh.repository;

public class ParticipantDbRepository : IParticipantRepository
{
    private static readonly ILog log = LogManager.GetLogger(nameof(ParticipantDbRepository));

    public ParticipantDbRepository()
    {
        log.Info("Creating ParticipantDbRepository");
    }

    public IEnumerable<Participant> findAll()
    {
        log.Info("Entering ParticipantDbRepository findAll");
        var con = DBUtils.getConnection();
        IList<Participant> participants = new List<Participant>();
        using (var comm = con.CreateCommand())
        {
            comm.CommandText = "select * from \"Participant\"";

            using (var dataR = comm.ExecuteReader())
            {
                while (dataR.Read())
                {
                    var id = dataR.GetString(0);
                    var userName = dataR.GetString(1);
                    var password = dataR.GetString(2);
                    var firstName = dataR.GetString(3);
                    var lastName = dataR.GetString(4);
                    var participant = new Participant(userName, password, firstName, lastName);
                    participant.Id = id;

                    participants.Add(participant);
                }
            }
        }

        log.InfoFormat("Exiting findAll with value={0}", string.Join(Environment.NewLine, participants));
        return participants;
    }

    public void save(Participant entity)
    {
        log.InfoFormat("Add participant={0}", entity);
        var con = DBUtils.getConnection();
        using (var comm = con.CreateCommand())
        {
            comm.CommandText =
                "insert into \"Participant\"(\"userName\",\"password\",\"firstName\",\"lastName\") values (@userName,@password,@firstname,@lastname)";
            comm.Parameters.Add(new NpgsqlParameter("@userName", entity.UserName));
            comm.Parameters.Add(new NpgsqlParameter("@password", entity.Password));
            comm.Parameters.Add(new NpgsqlParameter("@firstName", entity.FirstName));
            comm.Parameters.Add(new NpgsqlParameter("@lastname", entity.LastName));
            var result = comm.ExecuteNonQuery();
            if (result == 0)
                log.Error("Error Add Participant!");
            else
                log.Info("Successful adding!");
        }
    }

    public void update(Participant e1, Participant e2)
    {
        throw new NotImplementedException();
    }

    public Participant delete(Participant e)
    {
        throw new NotImplementedException();
    }

    public Participant findOne(string id)
    {
        log.InfoFormat("Entering findOne with value= {0}", id);
        var con = DBUtils.getConnection();
        using (var comm = con.CreateCommand())
        {
            comm.CommandText = "select * from \"Participant\" where id=@id";
            comm.Parameters.Add(new NpgsqlParameter("@id", id));
            using (var dataR = comm.ExecuteReader())
            {
                if (dataR.Read())
                {
                    var userName = dataR.GetString(1);
                    var password = dataR.GetString(2);
                    var firstName = dataR.GetString(3);
                    var lastName = dataR.GetString(4);
                    var participant = new Participant(userName, password, firstName, lastName);
                    participant.Id = id;

                    log.InfoFormat("Exiting findOne with value {0}", participant);
                    return participant;
                }
            }
        }

        log.InfoFormat("Exiting findOne with value {0}", null);
        return null;
    }
}